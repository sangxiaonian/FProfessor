package finance.com.fp.mode.bean;

import java.util.List;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/26 15:53
 */
public class FriendBean {


        /**
         * id : 3
         * title : 123123
         * updatetime : 1487645432
         * content : 12312313
         * images : [{"url":"http://localhost/phpcms/uploadfile/2017/0221/20170221105014683.jpg","alt":"欢迎扫描"},{"url":"http://localhost/phpcms/uploadfile/2017/0221/20170221105014924.jpg","alt":"欢迎扫描"}]
         */

        private String id;
        private String title;
        private String updatetime;
        private String content;
        private List<ImagesBean> images;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * url : http://localhost/phpcms/uploadfile/2017/0221/20170221105014683.jpg
             * alt : 欢迎扫描
             */

            private String url;
            private String alt;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }
        }

}
