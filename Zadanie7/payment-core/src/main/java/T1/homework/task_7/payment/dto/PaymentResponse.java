package T1.homework.task_7.payment.dto;

public class PaymentResponse {
    private String message;
    private boolean success;

    public PaymentResponse() {}

    public PaymentResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}