package urhobo.names.meanings.nigeria.urhobonamesmeaningsnaija;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DictionaryDatabase  extends SQLiteAssetHelper {

    private static final String DATABASE_NAMES = "name";
    private static final int DATABASE_VERSION = 5;

    public DictionaryDatabase(Context context) {
        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

}
