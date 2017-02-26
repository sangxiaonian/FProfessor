package finance.com.fp.mode.bean;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/23 17:13
 */
public class AliMsgBean {


    /**
     * alibaba_aliqin_fc_sms_num_send_response : {"result":{"err_code":"0","model":"106053163941^1108209086465","success":true},"request_id":"2ms6pk9q62fb"}
     */

    private AlibabaAliqinFcSmsNumSendResponseBean alibaba_aliqin_fc_sms_num_send_response;

    public AlibabaAliqinFcSmsNumSendResponseBean getAlibaba_aliqin_fc_sms_num_send_response() {
        return alibaba_aliqin_fc_sms_num_send_response;
    }

    public void setAlibaba_aliqin_fc_sms_num_send_response(AlibabaAliqinFcSmsNumSendResponseBean alibaba_aliqin_fc_sms_num_send_response) {
        this.alibaba_aliqin_fc_sms_num_send_response = alibaba_aliqin_fc_sms_num_send_response;
    }

    public static class AlibabaAliqinFcSmsNumSendResponseBean {
        /**
         * result : {"err_code":"0","model":"106053163941^1108209086465","success":true}
         * request_id : 2ms6pk9q62fb
         */

        private ResultBean result;
        private String request_id;

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public static class ResultBean {
            /**
             * err_code : 0
             * model : 106053163941^1108209086465
             * success : true
             */

            private String err_code;
            private String model;
            private boolean success;

            public String getErr_code() {
                return err_code;
            }

            public void setErr_code(String err_code) {
                this.err_code = err_code;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }
        }
    }
}
