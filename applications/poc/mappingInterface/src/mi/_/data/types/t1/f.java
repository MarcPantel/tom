package mi._.data.types.t1;

import mi._.data.types.T1;
import mi._.data.types.T2;

/**
 * @author nvintila
 * @date 2:55:14 PM Jun 13, 2009
 */
public class f extends T1 {

    T1 s1;

    T2 s2;

    public f(T1 s1, T2 s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public T1 getS1() {
        return s1;
    }

    public void setS1(T1 s1) {
        this.s1 = s1;
    }

    public T2 getS2() {
        return s2;
    }

    public void setS2(T2 s2) {
        this.s2 = s2;
    }

}
