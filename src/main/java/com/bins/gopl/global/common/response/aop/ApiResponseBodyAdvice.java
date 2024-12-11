package com.bins.gopl.global.common.response.aop;

import com.bins.gopl.global.common.response.ApiEnum;
import com.bins.gopl.global.common.response.ApiResponse;
import com.bins.gopl.global.common.response.BaseResponse;
import com.bins.gopl.global.error.GlobalExceptionAdvice;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;


/**
 * 특정 조건에 따라 응답의 본문을 가공 및 필터링하거나 변환하는 역할을 수행하는 클래스
 *
 * @author 박 수 빈
 * @version 1.0.0
 */

@RestControllerAdvice(basePackages = "com.bins.gopl.domain.**.controller", basePackageClasses = GlobalExceptionAdvice.class)
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 컨트롤러에서 작업이 끝난 후, 응답 본문을 수정하기 위한 조건에 따라 {@code beforeBodyWrite()}로 응답을 보낼지 판단한다.
     *
     * @param returnType        컨트롤러 메소드의 반환 타입에 대한 정보를 담고 있는 객체
     * @param converterType     반환되는 데이터의 직렬화되는 방식에 대한 정보
     * @return                  {@code true} 반환시, {@code beforeBodyWrite()} 호출되며, {@code false} 반환시엔 호출되지 않는다.
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> type = returnType.getParameterType(); // 컨트롤러 메소드의 반환타입을 가져온다.
        /* 컨트롤러 메소드의 반환타입이 ResponseEntity의 하위 클래스, 즉 ResponseEntity가 반환되는 타입이라면 */
        if (ResponseEntity.class.isAssignableFrom(type)) {
            try {
                /* 제네릭 타입 정보를 가져와 그 중 첫 번째 타입 인수를 추출해 그 타입에 해당되는 클래스 정보를 가져온다. */
                ParameterizedType parameterizedType = (ParameterizedType) returnType.getGenericParameterType();
                type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return !BaseResponse.class.isAssignableFrom(type);
    }

    /**
     * 컨트롤러 메소드가 반환한 응답 본문을 수정한다.
     * <p>- 성공 응답 객체의 {@code data}필드가 {@code null}인 상태에서 {@code data}필드가 단일 객체 형식인지 리스트 객체 형식인지에 따라
     * <b>빈 객체</b> 또는 <b>빈 배열</b>을 {@code data}필드에 할당하여 성공 응답 객체를 반환하도록 처리한다.</p>
     *
     *
     * @param body                      반환되는 본문 데이터
     * @param returnType                컨트롤러 메소드의 반환타입에 대한 정보를 담고 있는 객체
     * @param selectedContentType       'application/json'이나 'application/xml'과 같은 응답의 컨텐츠 타입
     * @param selectedConverterType     데이터를 직렬화 또는 역직렬화 처리시 사용되는 HTTP 메시지 컨터버 타입
     * @param request                   HTTP 요청 정보
     * @param response                  HTTP 응답 정보
     * @return                          수정된 본문 데이터 또는 원본 데이터
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        /* ApiEnum 값을 찾아 반환 처리 */
        final HttpServletResponse servletResponse = ((ServletServerHttpResponse)response).getServletResponse();
        final HttpStatus status = HttpStatus.resolve(servletResponse.getStatus());
        final ApiEnum apiEnum = ApiEnum.from(status);

        /* 성공 응답의 응답 결과 데이터의 빈 객체 또는 빈 배열 처리 */
        if (body instanceof ApiResponse<?> apiResponse) {
            final Object data = apiResponse.getData();
            if (data == null) {
                apiResponse = ApiResponse.success(apiEnum, generateDefaultData(returnType));
            }
            return apiResponse;
        } else { // 성공 응답 객체가 아니라면 에러 응답 객체이기에, 이땐 그냥 응답 본문을 반환한다.
            return body;
        }
    }

    /**
     * 성공 응답 객체의 응답 결과 데이터가 {@code null}일 경우 담을 빈 객체 또는 빈 배열을 반환한다.
     *
     * @param returnType    컨트롤러 메소드의 반환타입
     * @return              빈 배열 또는 빈 객체
     */
    private Object generateDefaultData(MethodParameter returnType) {
        return returnType.getParameterType().isAssignableFrom(List.class) ? Collections.emptyList() : Collections.emptyMap();
    }
}