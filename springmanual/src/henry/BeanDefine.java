package henry;

public class BeanDefine {
    // 存储scope类型
    private String scope;
    //存储类对象
    private Class clazz;

    public String getScope() {

        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
