package urhobo.names.meanings.nigeria.urhobonamesmeaningsnaija;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class DbBackend extends DbObject{

    public DbBackend(Context context) {
        super(context);
    }

    public String[] dictionaryWords(){
        String query = "Select * from dictionary";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                wordTerms.add(word);
            }while(cursor.moveToNext());
        }
        cursor.close();
        String[] dictionaryWords = new String[wordTerms.size()];
        dictionaryWords = wordTerms.toArray(dictionaryWords);
        return dictionaryWords;
    }

    public QuizObject getQuizById(int quizId){

        QuizObject quizObject = null;
        String query = "select * from dictionary where id = " + quizId;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String meaning = cursor.getString(cursor.getColumnIndexOrThrow("meaning"));
                quizObject = new QuizObject(word, meaning);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return quizObject;
    }


    public QuizObject getQuizByName(String quizName){

        QuizObject quizObject = null;
        String query = "select * from dictionary where name = '"+quizName+"'";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
              String word = quizName;
                String meaning = cursor.getString(cursor.getColumnIndexOrThrow("meaning"));
                quizObject = new QuizObject(word, meaning);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return quizObject;
    }

}
