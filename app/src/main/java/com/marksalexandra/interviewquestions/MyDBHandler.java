package com.marksalexandra.interviewquestions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * This class handles the creates a sql tables, loads data to it
 * creates an array list from added data
 * and handles logic for looking up the item
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "questionTerms.db";
    private static final String TABLE_TERMS = "terms";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TERMNAME = "termname";
    public static final String COLUMN_TERMMEANING = "termmeaning";

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    /**
     implemented so that the products table is created when the database
     is first initialized. This involves constructing a SQL CREATE
     statement containing instructions to create a new table with the
     appropriate columns and then passing that through to the execSQL()
     method of the SQLiteDatabase object passed as an argument to onCreate():
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TERMS_TABLE = "CREATE TABLE " +
                TABLE_TERMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_TERMNAME
                + " TEXT," + COLUMN_TERMMEANING + " TEXT" + ")";


        db.execSQL(CREATE_TERMS_TABLE);

        // load dummy data

        db.execSQL("INSERT INTO " + TABLE_TERMS + " VALUES (1, 'interface', 'A Java interface is a bit like a class, except a Java interface can only contain method signatures and fields. An Java interface cannot contain an implementation of the methods, only the signature (name, parameters and exceptions) of the method. You can use interfaces in Java as a way to achieve polymorphism.')");
        db.execSQL("INSERT INTO " + TABLE_TERMS + " VALUES (2, 'abstract', 'A Java keyword used in a class definition to specify that a class is not to be instantiated, but rather inherited by other classes. An abstract class can have abstract methods that are not implemented in the abstract class, but in subclasses. ')");
        db.execSQL("INSERT INTO " + TABLE_TERMS + " VALUES (3, 'abstract class', 'A class that contains one or more abstract methods , and therefore can never be instantiated. Abstract classes are defined so that other classes can extend them and make them concrete by implementing the abstract methods. \n')");
        db.execSQL("INSERT INTO " + TABLE_TERMS + " VALUES (4, 'applet', 'A component that typically executes in a Web browser, but can execute in a variety of other applications or devices that support the applet programming model. ')");
        db.execSQL("INSERT INTO " + TABLE_TERMS + " VALUES (5, 'bean', 'A reusable software component that conforms to certain design and naming conventions. The conventions enable beans to be easily combined to create an application using tools that understand the conventions. ')");
        db.execSQL("INSERT INTO " + TABLE_TERMS + " VALUES (6, 'casting', 'Explicit conversion from one data type to another. ')");
        db.execSQL("INSERT INTO " + TABLE_TERMS + " VALUES (7, 'constructor', 'A pseudo-method that creates an object. In the Java programming language, constructors are instance methods with the same name as their class. Constructors are invoked using the new keyword.')");
        db.execSQL("INSERT INTO " + TABLE_TERMS + " VALUES (8, 'garbage collection', 'The automatic detection and freeing of memory that is no longer in use. The Java runtime system performs garbage collection so that programmers never explicitly free objects. ')");
        db.execSQL("INSERT INTO " + TABLE_TERMS + " VALUES (9, 'JAR', 'JAR (Java Archive) is a platform-independent file format that aggregates many files into one. Multiple applets written in the Java programming language, and their requisite components (.class files, images, sounds and other resource files) can be bundled in a JAR file and subsequently downloaded to a browser in a single HTTP transaction. It also supports file compression and digital signatures. ')");


        //call to class to create dummy data (read txt file which creates productArray) and
        //        // then return the product
        //        //addDummyData(productArray);

    }


    /**
     *   called when the handler is invoked with a greater database version
     *   number from the one previously used. The exact steps to be performed
     *   in this instance will be application specific, so for the purposes
     *   of this example we will simply remove the old database and create a new one
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        onCreate(db);
    }


    /**
     *  insert database records
     *
     * @param questionsModel  an instance of our Product data model class
     */
    public void addTerm(InterviewQuestionsModel questionsModel) {

        ContentValues values = new ContentValues();
        //content object primed with key-value pairs for the data columns extracted from the questionsModel object
        values.put(COLUMN_TERMNAME, questionsModel.getTerm());
        values.put(COLUMN_TERMMEANING, questionsModel.getTermMeaning());

        //  a reference to the database will be obtained
        SQLiteDatabase db = this.getWritableDatabase();

        // insert the record
        db.insert(TABLE_TERMS, null, values);
        // close the database
        db.close();
    }

    /**
     *   query the questionsModel database
     *
     * @param termname  String object containing the name of the product to be located
     * @return
     */
    public ArrayList<InterviewQuestionsModel> findTerm(String termname ) {
        String query;
        if(termname.isEmpty()){ //in case nothing is entered
            query = "Select * FROM " + TABLE_TERMS;
        } else { //if something is entered, check if it is in db

            query = "Select * FROM " + TABLE_TERMS + " WHERE " + COLUMN_TERMNAME + " LIKE  \"%" + termname + "%\"";

        }

        /**
         * getWritableDatabase returns SQLiteDatabase which provides methods to execute SQL queries for SQLite.
         */
        SQLiteDatabase db = this.getWritableDatabase();

        /**
         * A cursor is a temporary work area created in the system memory when a SQL statement is executed.
         * A cursor contains information on a select statement and the rows of data accessed by it.
         * This temporary work area is used to store the data retrieved from the database, and manipulate this data.
         */
        Cursor cursor = db.rawQuery(query, null);


        ArrayList<InterviewQuestionsModel> termArray = new ArrayList<InterviewQuestionsModel>();
        InterviewQuestionsModel interviewQuestionsModel;

        /*
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            product = new InterviewQuestionsModel();
            product.setId(Integer.parseInt(cursor.getString(0)));
            product.setTerm(cursor.getString(1));
            product.setTermMeaning((cursor.getString(2)));
            productArray.add(product);

        }
        */


            //only the first match will then be returned, contained within a
            // new instance of our data model class
            //if cursor finds at least one data point (which in this case is always true b/c of loaded dummy data)
            //then loop through the records and create a list array.
            if (cursor.moveToFirst())
            {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    interviewQuestionsModel = new InterviewQuestionsModel();
                    interviewQuestionsModel.setId(Integer.parseInt(cursor.getString(0)));
                    interviewQuestionsModel.setTerm(cursor.getString(1));
                    interviewQuestionsModel.setTermMeaning((cursor.getString(2)));
                    termArray.add(interviewQuestionsModel);

                }
                cursor.close();

            } else {
                //
                termArray = null;
            }
            db.close();
            return termArray;
        }


}
