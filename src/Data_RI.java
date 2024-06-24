public class Data_RI {
    String IRname;
    String type;
    boolean result;

    public Data_RI(String IRname, String type, boolean result) {
        this.IRname = IRname;
        this.type = type;
        this.result = result;
    }

    // Getters y setters opcionales, dependiendo de tu uso
    public String getIRname() {
        return IRname;
    }

    public void setIRname(String IRname) {
        this.IRname = IRname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
