public class QOL {

    public void println(int message)        {System.out.println(message);}
    public void println(byte message)       {System.out.println(message);}
    public void println(char message)       {System.out.println(message);}
    public void println(long message)       {System.out.println(message);}
    public void println(float message)      {System.out.println(message);}
    public void println(short message)      {System.out.println(message);}
    public void println(double message)     {System.out.println(message);}
    public void println(String message)     {System.out.println(message);}
    public void println(boolean message)    {System.out.println(message);}

    public void print(int message)          {System.out.print(message);}
    public void print(byte message)         {System.out.print(message);}
    public void print(char message)         {System.out.print(message);}
    public void print(long message)         {System.out.print(message);}
    public void print(float message)        {System.out.print(message);}
    public void print(short message)        {System.out.print(message);}
    public void print(double message)       {System.out.print(message);}
    public void print(String message)       {System.out.print(message);}
    public void print(boolean message)      {System.out.print(message);}

    public void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    public void blank() {
        System.out.println();
    }
    public void blank(int size) {
        for (int i = 0; i < size; i++){
            blank();
        }
    }
    public void bar() {
        System.out.println("--------------------------------------------------");
    }
    public void bar(boolean thick) {
        if (thick) {
            System.out.println("==================================================");
        } else {
            System.out.println("--------------------------------------------------");
        }

    }
    public void bar(int size) {
        for (int i = 0; i < size; i++){
            System.out.print("-");
        }
    }
    public void bar(int size, boolean thick) {
        if (thick) {
            for (int i = 0; i < size; i++) {
                System.out.print("=");
            }
        } else {
            for (int i = 0; i < size; i++) {
                System.out.print("-");
            }
        }
    }

    public void flush() {System.out.flush();}

    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    

}
