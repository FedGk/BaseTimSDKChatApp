package com.yolo.kraus.bysjdemo01.Model;

import com.tencent.imsdk.ext.group.TIMGroupPendencyHandledStatus;
import com.tencent.imsdk.ext.group.TIMGroupPendencyItem;

/**
 * Created by Kraus on 2018/5/16.
 */

public class GroupFuture {
    private TIMGroupPendencyItem futureItem;

    private TIMGroupPendencyHandledStatus type;

    public GroupFuture(TIMGroupPendencyItem item){
        futureItem = item;
        type = item.getHandledStatus();
    }

    public TIMGroupPendencyHandledStatus getType() {
        return type;
    }

    public void setType(TIMGroupPendencyHandledStatus type) {
        this.type = type;
    }

    public TIMGroupPendencyItem getFutureItem() {
        return futureItem;
    }

    public void setFutureItem(TIMGroupPendencyItem futureItem) {
        this.futureItem = futureItem;
    }
}
