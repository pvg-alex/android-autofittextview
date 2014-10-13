package me.grantland.widget;

import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

/**
 * If a set of {@link me.grantland.widget.AutofitTextView}s are grouped then their text size is set
 * to the smallest text size in a group.
 */
public class AutofitGroup implements AutofitHelper.OnTextSizeChangeListener {

    private List<AutofitHelper> mHelpers = new ArrayList<AutofitHelper>();
    private float mGroupTextSize;

    /**
     * Adds an {@link me.grantland.widget.AutofitTextView} to this group
     */
    public void add(AutofitTextView autofitTextView) {
        AutofitHelper helper = autofitTextView.getAutofitHelper();
        addHelper(helper);
    }

    /**
     * Adds an {@link me.grantland.widget.AutofitHelper} to this group
     */
    public void addHelper(AutofitHelper helper) {
        mHelpers.add(helper);
        helper.onGrouped();
        helper.addOnTextSizeChangeListener(this);
        fitGroup();
    }

    @Override
    public void onTextSizeChange(float textSize, float oldTextSize) {
        fitGroup();
    }

    private void fitGroup() {
        float newGroupTextSize = Float.MAX_VALUE;
        for (AutofitHelper helper : mHelpers) {
            float size = helper.getAutofitTextSize();
            newGroupTextSize = Math.min(newGroupTextSize, size);
        }

        if (newGroupTextSize != mGroupTextSize) {
            mGroupTextSize = newGroupTextSize;
            for (AutofitHelper helper : mHelpers) {
                helper.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, mGroupTextSize);
            }
        }
    }
}
