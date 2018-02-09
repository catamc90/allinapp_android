package com.allin.adapter.interfaces;

/**
 * Created by harry on 11/29/17.
 */

public interface OnFeedEventListener {

    void onClick(int position);

    void onFavouriteClick(int position, boolean isStar);

}
