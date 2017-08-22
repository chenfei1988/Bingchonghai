package com.tobacco.xinyiyun.knowledge.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tobacco.xinyiyun.knowledge.mvp.common.entity.ContentEntity;
import com.tobacco.xinyiyun.knowledge.util.Utils;

/**
 * Created by YangQiang on 2017/8/21.
 */

@DatabaseTable
public class Collect {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public int userId;
    @DatabaseField
    public String title;
    @DatabaseField
    public String subTitle;
    @DatabaseField
    public String imgs;
    @DatabaseField
    public String url;
    @DatabaseField
    public int ywlx;
    @DatabaseField
    public String arg;
    @DatabaseField
    public String ywid;


    public ContentEntity convert() {
        ContentEntity mEntity = new ContentEntity();
        mEntity.arg = arg;
        mEntity.ywid = ywid;
        mEntity.url = url;
        mEntity.imgs = Utils.trim(imgs.split("#"));
        mEntity.subTitle = subTitle;
        mEntity.title = title;
        return mEntity;
    }

}
