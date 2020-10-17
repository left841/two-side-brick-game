package server.model;

public class BModelServer {
    private static IModelServer m = new ModelServer();
    public static IModelServer model() {
        return m;
    }
}
