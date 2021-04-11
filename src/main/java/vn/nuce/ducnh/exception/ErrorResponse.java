package vn.nuce.ducnh.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vn.nuce.ducnh.controllers.DTO.DucnhException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponse {
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private Map<String, Object> data;
    private List<ObjectError> errors;
    private String path;

    private ErrorResponse(int status, String exception) {
        this.data = new HashMap();
        this.errors = new ArrayList();
        this.status = status;
        this.exception = exception;
    }

    public ErrorResponse(DucnhException exception) {
        this(exception.getStatus().value(), exception.getClass().getName());
        this.message = exception.getMessage();
        this.errors = exception.getErrors();
        this.init(exception.getResponseStatusCode());
    }

    private void init(String responseStatusCode) {
        this.setTimestamp(LocalDateTime.now());
        this.setError(responseStatusCode);
        if (this.data != null) {
            this.setData(this.data);
        }

        if (this.errors != null) {
            this.setErrors(this.errors);
        }

        HttpServletRequest hreq = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        this.setPath(hreq.getRequestURI());
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public int getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }

    public String getException() {
        return this.exception;
    }

    public String getMessage() {
        return this.message;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public List<ObjectError> getErrors() {
        return this.errors;
    }

    public String getPath() {
        return this.path;
    }

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public void setException(final String exception) {
        this.exception = exception;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final Map<String, Object> data) {
        this.data = data;
    }

    public void setErrors(final List<ObjectError> errors) {
        this.errors = errors;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ErrorResponse)) {
            return false;
        } else {
            ErrorResponse other = (ErrorResponse) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getStatus() != other.getStatus()) {
                return false;
            } else {
                label97:
                {
                    Object this$timestamp = this.getTimestamp();
                    Object other$timestamp = other.getTimestamp();
                    if (this$timestamp == null) {
                        if (other$timestamp == null) {
                            break label97;
                        }
                    } else if (this$timestamp.equals(other$timestamp)) {
                        break label97;
                    }

                    return false;
                }

                Object this$error = this.getError();
                Object other$error = other.getError();
                if (this$error == null) {
                    if (other$error != null) {
                        return false;
                    }
                } else if (!this$error.equals(other$error)) {
                    return false;
                }

                Object this$exception = this.getException();
                Object other$exception = other.getException();
                if (this$exception == null) {
                    if (other$exception != null) {
                        return false;
                    }
                } else if (!this$exception.equals(other$exception)) {
                    return false;
                }

                label76:
                {
                    Object this$message = this.getMessage();
                    Object other$message = other.getMessage();
                    if (this$message == null) {
                        if (other$message == null) {
                            break label76;
                        }
                    } else if (this$message.equals(other$message)) {
                        break label76;
                    }

                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$errors = this.getErrors();
                Object other$errors = other.getErrors();
                if (this$errors == null) {
                    if (other$errors != null) {
                        return false;
                    }
                } else if (!this$errors.equals(other$errors)) {
                    return false;
                }

                Object this$path = this.getPath();
                Object other$path = other.getPath();
                if (this$path == null) {
                    if (other$path != null) {
                        return false;
                    }
                } else if (!this$path.equals(other$path)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ErrorResponse;
    }


    public String toString() {
        LocalDateTime var10000 = this.getTimestamp();
        return "ErrorResponse(timestamp=" + var10000 + ", status=" + this.getStatus() + ", error=" + this
                .getError() + ", exception=" + this.getException() + ", message=" + this.getMessage() + ", data=" + this
                .getData() + ", errors=" + this.getErrors() + ", path=" + this.getPath() + ")";
    }
}
