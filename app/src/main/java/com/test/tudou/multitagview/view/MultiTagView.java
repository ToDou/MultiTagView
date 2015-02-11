package com.test.tudou.multitagview.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
    private final int DEFAULT_TAG_PADDING = 12;
    private final int DEFAULT_TAG_MARGIN = 12;
    private final int DEFAULT_TAG_PADDING_TOP = 3;
    private final int DEFAULT_LAYOUT_MARGIN_TOP = 12;
    private final int DEFAULT_TAG_HEIGHT = 28;

    private int mEditTextWidth;
    private int tempWidth = 0;
    private LinearLayout mLayoutItem;
    private Context mContext;
    private int mTotalWidth;
    private ArrayList<Tag> tags;

    private int parentMargin;
    private String[] tagColors;
    private String buttonAddColor;
    private String buttonAddClickColor;
    private String tagTextColor;
    private String tagClickColor;
    private int tagPading;
    private int tagMargin;
    private int tagPaddingTop;
    private int tagPaddingBottom;
    private int tagPaddingRight;
    private int tagPaddingLeft;
    private int tagMarginTop;
    private int tagHeight;
    private Drawable deleteDrawable;
    private boolean tagClickable;
    private boolean showAddButton;

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

        if (attrs == null) return;
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiTagView, defStyleAttr, 0);
        if (a == null) return;

        setParentMargin(a.getDimensionPixelSize(R.styleable.MultiTagView_parentMargin, 12));
        setButtonAddColor(a.getString(R.styleable.MultiTagView_buttonAddColor));
        setButtonAddClickColor(a.getString(R.styleable.MultiTagView_buttonAddClickColor));
        setTagTextColor(a.getString(R.styleable.MultiTagView_tagTextColor));
        setTagClickColor(a.getString(R.styleable.MultiTagView_tagClickColor));
        setTagPading(a.getDimensionPixelSize(R.styleable.MultiTagView_tagPadding, DEFAULT_TAG_PADDING));
        setTagPaddingTop(a.getDimensionPixelSize(R.styleable.MultiTagView_tagPadding, DEFAULT_TAG_PADDING_TOP));
        setTagPaddingBottom(a.getDimensionPixelSize(R.styleable.MultiTagView_tagPadding, DEFAULT_TAG_PADDING_TOP));
        setTagPaddingRight(a.getDimensionPixelSize(R.styleable.MultiTagView_tagPadding, DEFAULT_TAG_PADDING));
        setTagPaddingLeft(a.getDimensionPixelSize(R.styleable.MultiTagView_tagPadding, DEFAULT_TAG_PADDING));
        setTagMarginTop(a.getDimensionPixelSize(R.styleable.MultiTagView_tagHeight, DEFAULT_TAG_HEIGHT));
        setDeleteDrawable(a.getDrawable(R.styleable.MultiTagView_deleteDrawable));


        init();
    }

    private void init() {
        int parentPadding = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        mTotalWidth = getDeviceWidth() - parentPadding * 2;
        tags = new ArrayList<>();
        mLayoutItem = new LinearLayout(mContext);
        mLayoutItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mLayoutItem);
        tagClickable = true;
        showAddButton = true;
        addEditText();
        //addClickButton();
    }

    private void addClickButton() {
        Button buttonInput = new Button(mContext);
        buttonInput.setPadding(dip2px(DEFAULT_TAG_PADDING), dip2px(DEFAULT_TAG_PADDING_TOP), dip2px(DEFAULT_TAG_PADDING), dip2px(DEFAULT_TAG_PADDING_TOP));
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
        mEditTextWidth = (int) (2 * dip2px(DEFAULT_TAG_PADDING) + buttonInput.getPaint().measureText("添加"));
        LayoutParams layoutParams = new LayoutParams(mEditTextWidth, dip2px(DEFAULT_TAG_HEIGHT));
        tempWidth += dip2px(DEFAULT_TAG_MARGIN) + mEditTextWidth; //add tag width

        if(tempWidth + dip2px(DEFAULT_TAG_MARGIN) > getDeviceWidth()){  //if out of screen, add a new layout
            mLayoutItem  = new LinearLayout(mContext);
            LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lParams.topMargin = dip2px(DEFAULT_LAYOUT_MARGIN_TOP);
            mLayoutItem.setLayoutParams(lParams);
            addView(mLayoutItem);
            tempWidth = (int) (2*dip2px(DEFAULT_TAG_PADDING) + buttonInput.getPaint().measureText(buttonInput.getText().toString()));
        }
        mLayoutItem.addView(buttonInput, layoutParams);
        tempWidth -= dip2px(DEFAULT_TAG_MARGIN) + mEditTextWidth;
    }

    /*
    add the tag by edit
     */
    private void addEditText() {
        if (!showAddButton) {
            return;
        }
        final EditText editText = new EditText(mContext);
        editText.setMinimumWidth(2);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setSingleLine();
        editText.setPadding(dip2px(DEFAULT_TAG_PADDING), dip2px(DEFAULT_TAG_PADDING_TOP), dip2px(DEFAULT_TAG_PADDING), dip2px(DEFAULT_TAG_PADDING_TOP));
        editText.setHint("添加");
        editText.setTextColor(getResources().getColor(android.R.color.white));
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        return false;
                    }
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
        mEditTextWidth = (int) (2 * dip2px(DEFAULT_TAG_PADDING) + editText.getPaint().measureText("添加"));
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, dip2px(DEFAULT_TAG_HEIGHT));

        tempWidth += dip2px(DEFAULT_TAG_MARGIN) + mEditTextWidth; //add tag width

        if(tempWidth - dip2px(DEFAULT_TAG_MARGIN) > mTotalWidth){  //if out of screen, add a new layout
            mLayoutItem  = new LinearLayout(mContext);
            LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lParams.topMargin = dip2px(DEFAULT_LAYOUT_MARGIN_TOP);
            mLayoutItem.setLayoutParams(lParams);
            addView(mLayoutItem);
            tempWidth = dip2px(DEFAULT_TAG_MARGIN) + mEditTextWidth;
        }
        mLayoutItem.addView(editText, layoutParams);
        editText.requestFocus();
        tempWidth -= dip2px(DEFAULT_TAG_MARGIN) + mEditTextWidth;
    }

    private void addTag(final Tag tag) {
        final Button button = new Button(mContext);
        button.setText(tag.content);
        button.setTextColor(getResources().getColor(android.R.color.white));
        button.setTextSize(15);
        StateRoundRectDrawable drawable = new StateRoundRectDrawable(Color.parseColor(DrawableUtils.getBackgoundColor(
                tag.content.hashCode())), Color.parseColor("#5d5d5d"));
        drawable.setDefautRadius(dip2px(DEFAULT_TAG_HEIGHT) / 2);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(drawable);
        } else {
            button.setBackgroundDrawable(drawable);
        }
        button.setPadding(dip2px(DEFAULT_TAG_PADDING), dip2px(DEFAULT_TAG_PADDING_TOP),
                dip2px(DEFAULT_TAG_PADDING), dip2px(DEFAULT_TAG_PADDING_TOP));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final FrameLayout frameLayout = (FrameLayout) button.getParent();
                if (frameLayout.getChildCount() > 1) {
                    frameLayout.removeAllViews();
                    refresh();
                    return;
                }
                final ImageView imageView = new ImageView(mContext);
                imageView.setImageResource(R.drawable.ic_delete);
                frameLayout.addView(imageView, new FrameLayout.LayoutParams((int)(dip2px(DEFAULT_TAG_HEIGHT) * 0.8), (int)(dip2px(DEFAULT_TAG_HEIGHT) * 0.8), Gravity.CENTER));
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation.setDuration(200);
                        imageView.startAnimation(scaleAnimation);
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
                                        frameLayout.removeView(imageView);
                                    }
                                })
                                .show();
                    }
                });
            }
        });
        button.setEnabled(tagClickable);

        int btnWidth = (int) (2*dip2px(DEFAULT_TAG_PADDING) + button.getPaint().measureText(button.getText().toString()));
        LayoutParams layoutParams = new LayoutParams(btnWidth, dip2px(DEFAULT_TAG_HEIGHT));
        FrameLayout frameLayout = new FrameLayout(mContext);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.addView(button);
        layoutParams.rightMargin = dip2px(DEFAULT_TAG_MARGIN);
        tempWidth += dip2px(DEFAULT_TAG_MARGIN) + btnWidth; //add tag width
        //the last tag margin right DEFAULT_BUTTON_MARGIN, don't forget
        if(tempWidth - dip2px(DEFAULT_TAG_MARGIN) > mTotalWidth){  //if out of screen, add a new layout
            mLayoutItem  = new LinearLayout(mContext);
            LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lParams.topMargin = dip2px(DEFAULT_LAYOUT_MARGIN_TOP);
            mLayoutItem.setLayoutParams(lParams);
            addView(mLayoutItem);
            tempWidth = dip2px(DEFAULT_TAG_MARGIN) + btnWidth;
        }
        mLayoutItem.addView(frameLayout, layoutParams);
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

    public void addTag(String s) {
        Tag tag = new Tag(tags.size(), s);
        tags.add(tag);
        refresh();
    }

    public void addTags(ArrayList<String> arrayList) {
        for (String s : arrayList) {
            Tag tag = new Tag(tags.size(), s);
            tags.add(tag);
        }
        refresh();
    }

    public void removeAllTagView() {
        tags.clear();
        refresh();
    }

    public void removeTagAt(int i) {
        tags.remove(i);
        refresh();
    }

    public void setShowAddButton(boolean show) {
        showAddButton = show;
        refresh();
    }

    public void setTagClickable(boolean able) {
        tagClickable = able;
        refresh();

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

    public int getTagHeight() {
        return tagHeight;
    }

    public void setTagHeight(int tagHeight) {
        this.tagHeight = tagHeight;
    }

    public int getTagMarginTop() {
        return tagMarginTop;
    }

    public void setTagMarginTop(int tagMarginTop) {
        this.tagMarginTop = tagMarginTop;
    }

    public int getTagPaddingTop() {
        return tagPaddingTop;
    }

    public void setTagPaddingTop(int tagPaddingTop) {
        this.tagPaddingTop = tagPaddingTop;
    }

    public int getTagMargin() {
        return tagMargin;
    }

    public void setTagMargin(int tagMargin) {
        this.tagMargin = tagMargin;
    }

    public int getTagPading() {
        return tagPading;
    }

    public void setTagPading(int tagPading) {
        this.tagPading = tagPading;
    }

    public String getTagClickColor() {
        return tagClickColor;
    }

    public void setTagClickColor(String tagClickColor) {
        this.tagClickColor = tagClickColor;
    }

    public String getTagTextColor() {
        return tagTextColor;
    }

    public void setTagTextColor(String tagTextColor) {
        this.tagTextColor = tagTextColor;
    }

    public String getButtonAddClickColor() {
        return buttonAddClickColor;
    }

    public void setButtonAddClickColor(String buttonAddClickColor) {
        this.buttonAddClickColor = buttonAddClickColor;
    }

    public String getButtonAddColor() {
        return buttonAddColor;
    }

    public void setButtonAddColor(String buttonAddColor) {
        this.buttonAddColor = buttonAddColor;
    }

    public String[] getTagColors() {
        return tagColors;
    }

    public void setTagColors(String[] tagColors) {
        this.tagColors = tagColors;
    }

    public int getParentMargin() {
        return parentMargin;
    }

    public void setParentMargin(int parentMargin) {
        this.parentMargin = parentMargin;
    }

    public int getTagPaddingBottom() {
        return tagPaddingBottom;
    }

    public void setTagPaddingBottom(int tagPaddingBottom) {
        this.tagPaddingBottom = tagPaddingBottom;
    }

    public int getTagPaddingRight() {
        return tagPaddingRight;
    }

    public void setTagPaddingRight(int tagPaddingRight) {
        this.tagPaddingRight = tagPaddingRight;
    }

    public int getTagPaddingLeft() {
        return tagPaddingLeft;
    }

    public void setTagPaddingLeft(int tagPaddingLeft) {
        this.tagPaddingLeft = tagPaddingLeft;
    }

    public Drawable getDeleteDrawable() {
        return deleteDrawable;
    }

    public void setDeleteDrawable(Drawable deleteDrawable) {
        this.deleteDrawable = deleteDrawable;
    }
}
