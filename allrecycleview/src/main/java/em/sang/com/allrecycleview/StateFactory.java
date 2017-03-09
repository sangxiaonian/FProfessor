package em.sang.com.allrecycleview;


import static em.sang.com.allrecycleview.BasicRefrushRecycleView.LOAD_BEFOR;
import static em.sang.com.allrecycleview.BasicRefrushRecycleView.LOAD_OVER;

public class StateFactory {
    public static StateFactory factory;

    public static StateFactory getInstance(){
        if (factory==null){
            synchronized (StateFactory.class){
                if (factory==null) {
                    factory=new StateFactory();
                }
            }
        }
        return factory;

    }

    public int upChangeStateByHeight(float height,float mearchTop,int style){
        int state;
        switch (style){
            case RefrushRecycleView.STYLE_PULL:
                state=getPullStyleState(height, mearchTop);
                break;
            case RefrushRecycleView.STYLE_SLIPE:
                state=getSlipeStyleState(height, mearchTop);
            default:
                state=getPullStyleState(height, mearchTop);
        }
        return state;
    }

    private int getSlipeStyleState(float height, float mearchTop) {
        return 0;
    }


    public int getPullStyleState(float height, float mearchTop) {
        int state;
        if (height > mearchTop) {
            state = LOAD_BEFOR;
        } else {
            state = LOAD_OVER;

        }
        return state;
    }
}
