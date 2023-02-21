public class Utils {
    public static int hash(int value){
        return value;
    }

    public static int sumWithOutOverflow(int a, int b){
        var value = a - (Integer.MAX_VALUE - b);
        return value > 0 ? value : Integer.MAX_VALUE + value;
    }

    public static double toDegree(int a) {
        return (double)a/(double)Integer.MAX_VALUE * 360.0;
    }
}
