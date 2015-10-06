package hugo.alberto.vai_vozao.Adapter;

/**
 * Created by Alberto on 30/09/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hugo.alberto.vai_vozao.Fragment.ElencoFragment;
import hugo.alberto.vai_vozao.Fragment.JogosFragment;
import hugo.alberto.vai_vozao.Fragment.TabelaFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                JogosFragment tab1 = new JogosFragment();
                return tab1;
            case 1:
                TabelaFragment tab2 = new TabelaFragment();
                return tab2;
           /* case 2:
                ElencoFragment tab3 = new ElencoFragment();
                return tab3;*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}