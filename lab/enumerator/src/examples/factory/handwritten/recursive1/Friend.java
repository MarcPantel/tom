package examples.factory.handwritten.recursive1;

import tom.library.factory.Enumerate;
import tom.library.factory.EnumerateGenerator;

public class Friend {

    private int no;
    private String name;
    private Friend friend;

    @Enumerate(canBeNull = true)
    public Friend(
        @Enumerate(maxSize = 8) int no,
        @Enumerate(maxSize = 6) Friend friend
    ) {
        this.no = no;
        this.friend = friend;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    
    public Friend getFriend() {
        return friend;
    }

    @Override
    public String toString() {
        return "StudentFriend [no=" + no + ", friend="+friend+ "]";
    }

}
