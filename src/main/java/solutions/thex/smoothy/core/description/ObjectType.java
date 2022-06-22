package solutions.thex.smoothy.core.description;

public enum ObjectType {

    DAO("dao"),//
    REPOSITORY("dao.repository"),//
    SERVICE("dao.service"),//
    CONTROLLEER("controller");

    private final String packageName;

    ObjectType(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return packageName.split("\\.")[packageName.split("\\.").length - 1];
    }

    public String getPackageName(){
        return packageName;
    }

}
