package id.ac.polban.jtk.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import id.ac.polban.jtk.nearestplace.R;

/**
 * Cursor Adapter untuk ListView Place dari cursor database
 * Source : https://guides.codepath.com/android/Populating-a-ListView-with-a-CursorAdapter
 *
 * @editor Mufid Jamaluddin
 */
public class PlaceCursorAdapter extends CursorAdapter
{
    /**
     * Konstruktor
     *
     * @param context
     * @param cursor
     */
    public PlaceCursorAdapter(Context context, Cursor cursor)
    {
        super(context,cursor,0);
    }

    /**
     *  The newView method is used to inflate a new view and return it,
     *  you don't bind any data to the view at this point.
     *
     * @param context
     * @param cursor
     * @param viewGroup
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
    }

    /**
     * The bindView method is used to bind all data to a given view
     * such as setting the text on a TextView.
     *
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {

    }
}
