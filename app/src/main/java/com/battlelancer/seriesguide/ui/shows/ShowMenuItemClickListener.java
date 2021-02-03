package com.battlelancer.seriesguide.ui.shows;

import android.content.Context;
import android.view.MenuItem;
import android.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import com.battlelancer.seriesguide.R;
import com.battlelancer.seriesguide.SgApp;
import com.battlelancer.seriesguide.provider.SeriesGuideContract;
import com.battlelancer.seriesguide.provider.SgRoomDatabase;
import com.battlelancer.seriesguide.sync.SgSyncAdapter;
import com.battlelancer.seriesguide.ui.episodes.EpisodeTools;
import com.battlelancer.seriesguide.ui.lists.ManageListsDialogFragment;

/**
* Item click listener for show item popup menu.
*/
public class ShowMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    private final Context context;
    private final ShowTools showTools;
    private final FragmentManager fragmentManager;
    private final long showId;
    private final long nextEpisodeId;

    public ShowMenuItemClickListener(Context context, FragmentManager fm, long showId,
            long nextEpisodeId) {
        this.context = context;
        this.fragmentManager = fm;
        this.showId = showId;
        this.nextEpisodeId = nextEpisodeId;
        this.showTools = SgApp.getServicesComponent(context).showTools();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int showTvdbId = SgRoomDatabase.getInstance(context).sgShow2Helper().getShowTvdbId(showId);
        int itemId = item.getItemId();
        if (itemId == R.id.menu_action_shows_watched_next) {
            EpisodeTools.episodeWatchedIfNotZero(context, nextEpisodeId);
            return true;
        } else if (itemId == R.id.menu_action_shows_favorites_add) {
            showTools.storeIsFavorite(showId, true);
            return true;
        } else if (itemId == R.id.menu_action_shows_favorites_remove) {
            showTools.storeIsFavorite(showId, false);
            return true;
        } else if (itemId == R.id.menu_action_shows_hide) {
            showTools.storeIsHidden(showId, true);
            return true;
        } else if (itemId == R.id.menu_action_shows_unhide) {
            showTools.storeIsHidden(showId, false);
            return true;
        } else if (itemId == R.id.menu_action_shows_manage_lists) {
            ManageListsDialogFragment.show(fragmentManager, showTvdbId,
                    SeriesGuideContract.ListItemTypes.SHOW);
            return true;
        } else if (itemId == R.id.menu_action_shows_update) {
            SgSyncAdapter.requestSyncSingleImmediate(context, true, showTvdbId);
            return true;
        } else if (itemId == R.id.menu_action_shows_remove) {
            RemoveShowDialogFragment.show(showId, fragmentManager, context);
            return true;
        }
        return false;
    }

}
