package vn.nuce.ducnh.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class ExceptionMessage {
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorKey;
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String params;

    public ExceptionMessage(String params, String message) {
        this.params = params;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.status = 400;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getErrorKey() {
        return this.errorKey;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getParams() {
        return this.params;
    }

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
    )
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setErrorKey(final String errorKey) {
        this.errorKey = errorKey;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setParams(final String params) {
        this.params = params;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ExceptionMessage)) {
            return false;
        } else {
            ExceptionMessage other = (ExceptionMessage) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71:
                {
                    Object this$status = this.getStatus();
                    Object other$status = other.getStatus();
                    if (this$status == null) {
                        if (other$status == null) {
                            break label71;
                        }
                    } else if (this$status.equals(other$status)) {
                        break label71;
                    }

                    return false;
                }

                Object this$timestamp = this.getTimestamp();
                Object other$timestamp = other.getTimestamp();
                if (this$timestamp == null) {
                    if (other$timestamp != null) {
                        return false;
                    }
                } else if (!this$timestamp.equals(other$timestamp)) {
                    return false;
                }

                label57:
                {
                    Object this$errorKey = this.getErrorKey();
                    Object other$errorKey = other.getErrorKey();
                    if (this$errorKey == null) {
                        if (other$errorKey == null) {
                            break label57;
                        }
                    } else if (this$errorKey.equals(other$errorKey)) {
                        break label57;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$params = this.getParams();
                Object other$params = other.getParams();
                if (this$params == null) {
                    if (other$params == null) {
                        return true;
                    }
                } else if (this$params.equals(other$params)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ExceptionMessage;
    }


    public String toString() {
        LocalDateTime var10000 = this.getTimestamp();
        return "ExceptionMessage(timestamp=" + var10000 + ", errorKey=" + this.getErrorKey() + ", status=" + this
                .getStatus() + ", message=" + this.getMessage() + ", params=" + this.getParams() + ")";
    }

    public ExceptionMessage() {
    }

    public ExceptionMessage(final LocalDateTime timestamp, final String errorKey, final Integer status,
                            final String message, final String params) {
        this.timestamp = timestamp;
        this.errorKey = errorKey;
        this.status = status;
        this.message = message;
        this.params = params;
    }
}
