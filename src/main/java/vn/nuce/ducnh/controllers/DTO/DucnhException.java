package vn.nuce.ducnh.controllers.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class DucnhException extends Exception implements Serializable {
    private static final long serialVersionUID = 2L;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    private LocalDateTime timestamp;
    private String responseStatusCode;
    private HttpStatus status;
    private Map<String, Object> data;
    private List<ObjectError> errors;

    public DucnhException(String responseStatusCode, HttpStatus status) {
        this.status = HttpStatus.BAD_REQUEST;
        this.responseStatusCode = responseStatusCode;
        this.status = status;
    }

    public DucnhException(String responseStatusCode, String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.responseStatusCode = responseStatusCode;
    }

    public DucnhException(String responseStatusCode, String message, Throwable throwable) {
        super(message, throwable);
        this.status = HttpStatus.BAD_REQUEST;
        this.responseStatusCode = responseStatusCode;
    }

    public DucnhException(String responseStatusCode, String message, List<ObjectError> errors) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.responseStatusCode = responseStatusCode;
        this.errors = errors;
    }

    public DucnhException(String responseStatusCode, HttpStatus status, Map<String, Object> data) {
        this.status = HttpStatus.BAD_REQUEST;
        this.responseStatusCode = responseStatusCode;
        this.status = status;
        this.data = data;
    }

    public DucnhException(String responseStatusCode, String message, HttpStatus status) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.responseStatusCode = responseStatusCode;
        this.status = status;
    }

    public DucnhException(String message, LocalDateTime timestamp, String responseStatusCode, String message2, HttpStatus status) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.timestamp = timestamp;
        this.responseStatusCode = responseStatusCode;
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getResponseStatusCode() {
        return this.responseStatusCode;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public List<ObjectError> getErrors() {
        return this.errors;
    }

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setResponseStatusCode(final String responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    public void setData(final Map<String, Object> data) {
        this.data = data;
    }

    public void setErrors(final List<ObjectError> errors) {
        this.errors = errors;
    }

    public String toString() {
        LocalDateTime var10000 = this.getTimestamp();
        return "DucnhException(timestamp=" + var10000 + ", responseStatusCode=" + this.getResponseStatusCode() + ", status=" + this.getStatus() + ", data=" + this.getData() + ", errors=" + this.getErrors() + ")";
    }
}
