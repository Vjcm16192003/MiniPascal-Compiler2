package Expression;

public class Data {
    Object data;
    int pos1;

    int scope;

    public Data(Object data, int scope){
        this.data = data;
        this.scope = scope;
    }

    public Integer toInteger(){
        return (Integer) data;
    }

    public Boolean toBoolean(){
        return (Boolean) data;
    }

    public Character toChar(){
        return (Character) data;
    }

    public Data[] toArray(){return (Data[]) data;}

    public java.lang.String instanceOf(){
        try{
            this.toInteger();
            return "integer";
        } catch (Exception e){
            try{
                this.toBoolean();
                return "boolean";
            } catch (Exception e2){
                try{
                    this.toChar();
                    return "char";
                } catch (Exception e3){
                    try{
                        this.toArray();
                        return "array";
                    } catch (Exception e4){
                        try{
                            this.toString();
                            return "string";
                        } catch (Exception e5){
                            return "unknown";
                        }
                    }
                }
            }
        }

    }

    // For for-loop temp vars
    public void add1(){
        this.data = (Integer) this.data + 1;
    }

    public void sub1(){
        this.data = (Integer) this.data - 1;
    }

    public boolean equals (Object obj) {
        if (data == obj) return true;
        if (data == null || this.getClass() != obj.getClass()) return false;
        Data compare = (Data) obj ;
        return this.data.equals(compare.data);
    }

    public int getPos1() {return pos1;}

    public void setPos1(int pos1) {this.pos1 = pos1;}

    public int getScope() {return scope;}


    @Override
    public java.lang.String toString() {
        return java.lang.String.valueOf(data);
    }
}
