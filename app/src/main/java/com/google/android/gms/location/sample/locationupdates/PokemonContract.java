package com.google.android.gms.location.sample.locationupdates;

import android.provider.BaseColumns;

/**
 * Created by owner on 6/1/16.
 */
public final class PokemonContract {

    PokemonContract(){};

    public static abstract class Pokemon implements BaseColumns {
        public static final String TABLE_NAME = "PokemonList";
        public static final String RANK = "Rank";
        public static final String LEVEL1 = "Lv1";
        public static final String LEVEL2 = "Lv2";
        public static final String LEVEL3 = "Lv3";
        public static final String TYPE_ID = "Type_ID";
    }

    public static abstract class Type implements BaseColumns{
        public static final String TABLE_NAME = "TypeList";
        public static final String CODE = "Code";
        public static final String NAME = "Name";
    }

    public static abstract class CaptureList implements BaseColumns{
        public static final String TABLE_NAME = "CaptureList";
        public static final String RANK = "Rank";
        public static final String LEVEL = "Level";
        public static final String NAME = "Name";
        public static final String TYPE_ID = "Type_ID";
        public static final String TIME = "TimeStamp";
        public static final String CATEGORY = "Category";
    }

    public static abstract class QuestionList implements BaseColumns{
        public static final String TABLE_NAME = "QuestionList";
        public static final String QNO = "q_no";
        public static final String QUESTION = "question";
        public static final String ANSWER = "answer";
        public static final String IMAGE = "image";
        public static final String SCORE = "score";
    }

    public static abstract class SolvedList implements BaseColumns{
        public static final String TABLE_NAME = "SolvedList";
        public static final String QNO = "q_no";
        public static final String TIME = "TimeStamp";
        public static final String SCORE = "score";
    }
}
