package com.test.tudou.multitagview.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.tudou.multitagview.R;
import com.test.tudou.multitagview.drawable.StateRoundRectDrawable;
import com.test.tudou.multitagview.model.Tag;
import com.test.tudou.multitagview.util.DrawableUtils;

import java.util.ArrayList;

/**
 * Created by tudou on 15-1-3.
 */
public class MultiTagView extends LinearLayout {
    private final int DEFAULT_BUTTON_PADDING = 12;
    private final int DEFAULT_BUTTON_MARGIN = 12;
    private final int DEFAULT_BUTTON_PADDING_TOP = 3;
    private final int DEFAULT_LAYOUT_MARGIN_TOP = 12;
    private final int DEFAULT_TAG_HEIGHT = 28;

    private ArrayList<Tag> tags;

    private int mEditTextWidth;
    private int tempWidth = 0;
    private LinearLayout mLayoutItem;
    private Context mContext;

    public MultiTagView(Context context) {
        this(context, null);
    }

    public MultiTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mContext = context;
        init();
    }

    private void init() {
        tags = new ArrayList<>();
        mLayoutItem = new LinearLayout(mContext);
        mLayoutItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mLayoutItem);
        addEditText();
        //addClickButton();
    }

    private void addClickButton() {
        Button buttonInput = new Button(mContext);
        buttonInput.setPadding(dip2px(DEFAULT_BUTTON_PADDING), dip2px(DEFAULT_BUTTON_PADDING_TOP), dip2px(DEFAULT_BUTTON_PADDING), dip2px(DEFAULT_BUTTON_PADDING_TOP));
        buttonInput.setText("添加");
        buttonInput.setTextColor(Color.parseColor("#ffffff"));
        StateRoundRectDrawable drawable = new StateRoundRectDrawable(Color.parseColor("#666666"), Color.parseColor("#5d5d5d"));
        drawable.setDefautRadius(dip2px(DEFAULT_TAG_HEIGHT) / 2);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            buttonInput.setBackground(drawable);
        } else {
            buttonInput.setBackgroundDrawable(drawable);
        }

        buttonInput.setTextSize(15);
        buttonInput.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(mContext);
                new AlertDialog.Builder(mContext)
                        .setMessage("输入内容")
                        .setView(editText)
                        .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!editText.getText().toString().trim().equals("")) {
                                    Tag tag = new Tag(tags.size(), editText.getText().toString().trim());
                                    tags.add(tag);
                                    refresh();
                                }
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        mEditTextWidth = (int) (2 * dip2px(DEFAULT_BUTTON_PADDING) + buttonInput.getPaint().measureText("添加"));
        LayoutParams layoutParams = new LayoutParams(mEditTextWidth, dip2px(DEFAULT_TAG_HEIGHT));
        tempWidth += dip2px(DEFAULT_BUTTON_MARGIN) + mEditTextWidth; //add tag width
        //the last tag margin right DEFAULT_BUTTON_MARGIN, don't forget
        if(tempWidth + dip2px(DEFAULT_BUTTON_MARGIN) > getDeviceWidth()){  //if out of screen, add a new layout
            mLayoutItem  = new LinearLayout(mContext);
            LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lParams.topMargin = dip2px(DEFAULT_LAYOUT_MARGIN_TOP);
            mLayoutItem.setLayoutParams(lParams);
            addView(mLayoutItem);
            tempWidth = (int) (2*dip2px(DEFAULT_BUTTON_PADDING) + buttonInput.getPaint().measureText(buttonInput.getText().toString()));
        }
        mLayoutItem.addView(buttonInput, layoutParams);
        tempWidth -= dip2px(DEFAULT_BUTTON_MARGIN) + mEditTextWidth;
    }

    private void addEditText() {
        //Button buttonInput = new Button(mContext);
        final EditText editText = new EditText(mContext);
        editText.setMinimumWidth(2);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setSingleLine();
        editText.setPadding(dip2px(DEFAULT_BUTTON_PADDING), dip2px(DEFAULT_BUTTON_PADDING_TOP), dip2px(DEFAULT_BUTTON_PADDING), dip2px(DEFAULT_BUTTON_PADDING_TOP));
        editText.setHint("添加");
        editText.setTextColor(Color.parseColor("#ffffff"));
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Tag tag = new Tag(tags.size(), editText.getText().toString().trim());
                    tags.add(tag);
                    refresh();
                    return true;
                }
                return false;
            }
        });
        StateRoundRectDrawable drawable = new StateRoundRectDrawable(Color.parseColor("#666666"), Color.parseColor("#5d5d5d"));
        drawable.setDefautRadius(dip2px(DEFAULT_TAG_HEIGHT) / 2);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(drawable);
        } else {
            editText.setBackgroundDrawable(drawable);
        }
        editText.setTextSize(15);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StateRoundRectDrawable drawable = new StateRoundRectDrawable(Color.parseColor("#666666"), Color.parseColor("#5d5d5d"));
                drawable.setDefautRadius(dip2px(DEFAULT_TAG_HEIGHT) / 2);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    editText.setBackground(drawable);
                } else {
                    editText.setBackgroundDrawable(drawable);
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
        mEditTextWidth = (int) (2 * dip2px(DEFAULT_BUTTON_PADDING) + editText.getPaint().measureText("添加"));
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, dip2px(DEFAULT_TAG_HEIGHT));

        tempWidth += dip2px(DEFAULT_BUTTON_MARGIN) + mEditTextWidth; //add tag width
        //the last tag margin right DEFAULT_BUTTON_MARGIN, don't forget
        if(tempWidth + dip2px(DEFAULT_BUTTON_MARGIN) > getDeviceWidth()){  //if out of screen, add a new layout
            mLayoutItem  = new LinearLayout(mContext);
            LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lParams.topMargin = dip2px(DEFAULT_LAYOUT_MARGIN_TOP);
            mLayoutItem.setLayoutParams(lParams);
            addView(mLayoutItem);
            tempWidth = (int) (2*dip2px(DEFAULT_BUTTON_PADDING) + editText.getPaint().measureText(editText.getText().toString()));
        }
        mLayoutItem.addView(editText, layoutParams);
        editText.requestFocus();
        tempWidth -= dip2px(DEFAULT_BUTTON_MARGIN) + mEditTextWidth;
    }

    private void addTag(final Tag tag) {
        Button button = new Button(mContext);
        button.setText(tag.content);
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setTextSize(15);
        StateRoundRectDrawable drawable = new StateRoundRectDrawable(Color.parseColor(DrawableUtils.getBackgoundColor(
                tag.content.hashCode())), Color.parseColor("#5d5d5d"));
        drawable.setDefautRadius(dip2px(DEFAULT_TAG_HEIGHT) / 2);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(drawable);
        } else {
            button.setBackgroundDrawable(drawable);
        }
        button.setPadding(dip2px(DEFAULT_BUTTON_PADDING), dip2px(DEFAULT_BUTTON_PADDING_TOP),
                dip2px(DEFAULT_BUTTON_PADDING), dip2px(DEFAULT_BUTTON_PADDING_TOP));
        button.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setMessage("Are you sure to delete?")
                        .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tags.remove(tag);
                                refresh();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                return false;
            }
        });

        int btnWidth = (int) (2*dip2px(DEFAULT_BUTTON_PADDING) + button.getPaint().measureText(button.getText().toString()));
        LayoutParams layoutParams = new LayoutParams(btnWidth, dip2px(DEFAULT_TAG_HEIGHT));
        layoutParams.rightMargin = dip2px(DEFAULT_BUTTON_MARGIN);
        tempWidth += dip2px(DEFAULT_BUTTON_MARGIN) + btnWidth; //add tag width
        //the last tag margin right DEFAULT_BUTTON_MARGIN, don't forget
        int parentPadding = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        if(tempWidth > dip2px(this.getWidth())){  //if out of screen, add a new layout
            mLayoutItem  = new LinearLayout(mContext);
            LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lParams.topMargin = dip2px(DEFAULT_LAYOUT_MARGIN_TOP);
            mLayoutItem.setLayoutParams(lParams);
            addView(mLayoutItem);
            tempWidth = (int) (2 * dip2px(DEFAULT_BUTTON_PADDING) + button.getPaint().measureText(button.getText().toString()));
        }
        mLayoutItem.addView(button, layoutParams);
    }

    private void refresh() {
        removeAllViews();
        mLayoutItem = new LinearLayout(mContext);
        mLayoutItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mLayoutItem);
        tempWidth = 0;
        for (Tag tag : tags) {
            addTag(tag);
        }
        addEditText();
        //addClickButton();
    }

    public ArrayList<String> getTags() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Tag tag : tags) {
            arrayList.add(tag.content);
        }
        return arrayList;
    }

    private int getDeviceWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    private int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
