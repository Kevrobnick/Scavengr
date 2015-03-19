package kevrobnick.com.database;

/**
 * Created by Kevin on 3/12/2015.
 * Edited by Rob on 3/18/2015
 */
public class Game {

    int _id;
    String _name;
    byte[] _image;

    public Game(int keyId, String name, byte[] image){
        this._id = keyId;
        this._name = name;
        this._image = image;

    }
    public Game(String name, byte[] image){
        this._name = name;
        this._image = image;
    }
    public Game(int keyId){
        this._id=keyId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }
}
