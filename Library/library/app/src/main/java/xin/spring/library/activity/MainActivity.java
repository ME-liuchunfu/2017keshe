package xin.spring.library.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import xin.spring.library.fragment.InputFragment;
import xin.spring.library.fragment.ListFragment;
import xin.spring.library.listener.OnFragmentInteractionListener;
import xin.spring.library.R;
import xin.spring.library.fragment.SearchFragment;
import xin.spring.library.dao.LibraryDBDao;


public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    // Fragment管理器
    private FragmentManager fragmentManager;
    // fragments
    private InputFragment inputFragment;
    private ListFragment listFragment;
    private SearchFragment searchFragment;
    private LibraryDBDao mDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDao = new LibraryDBDao(this);
        inputFragment = InputFragment.newInstance();
        listFragment = ListFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
        // 获取Fragment管理器
        this.fragmentManager = this.getSupportFragmentManager();
        // 设置默认显示的fragment
        this.fragmentManager.beginTransaction().replace(R.id.fragmentContainer , this.inputFragment).commit();
        RadioButton inputButton = (RadioButton)findViewById(R.id.tabBar_firstBtn);
        inputButton.setTextColor(Color.parseColor("#5fb39d"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDao!=null)
            mDao.destoryDB();
    }

    /**
     * 如果菜单存在，设置自定义菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * tabBar 录入图书按钮 点击事件
     */
    public void tabBarInputButtonClicked(View view) {
        // 修改按钮字体颜色
        RadioButton button = (RadioButton) view;
        button.setTextColor(Color.parseColor("#5fb39d"));
        RadioButton listButton = (RadioButton)findViewById(R.id.tabBar_secondBtn);
        listButton.setTextColor(Color.parseColor("#666666"));
        RadioButton searchButton = (RadioButton)findViewById(R.id.tabBar_thirdBtn);
        searchButton.setTextColor(Color.parseColor("#666666"));
        // 切换fragment
        this.fragmentManager.beginTransaction().replace(R.id.fragmentContainer , this.inputFragment).commit();
    }
    /*
     * tabBar 图书列表按钮 点击事件
     */
    public void tabBarListButtonClicked(View view) {
        // 修改按钮字体颜色
        RadioButton currentButton = (RadioButton)view;
        currentButton.setTextColor(Color.parseColor("#5fb39d"));
        RadioButton inputButton = (RadioButton)findViewById(R.id.tabBar_firstBtn);
        inputButton.setTextColor(Color.parseColor("#666666"));
        RadioButton searchButton = (RadioButton)findViewById(R.id.tabBar_thirdBtn);
        searchButton.setTextColor(Color.parseColor("#666666"));
        // 切换fragment
        this.fragmentManager.beginTransaction().replace(R.id.fragmentContainer , this.listFragment).commit();
    }
    /*
     * tabBar 搜索图书 点击事件
     */
    public void tabBarSearchButtonClicked(View view) {
        // 修改按钮字体颜色
        RadioButton currentButton = (RadioButton)view;
        currentButton.setTextColor(Color.parseColor("#5fb39d"));
        RadioButton inputButton = (RadioButton)findViewById(R.id.tabBar_firstBtn);
        inputButton.setTextColor(Color.parseColor("#666666"));
        RadioButton listButton = (RadioButton)findViewById(R.id.tabBar_secondBtn);
        listButton.setTextColor(Color.parseColor("#666666"));
        // 切换fragment
        this.fragmentManager.beginTransaction().replace(R.id.fragmentContainer , this.searchFragment).commit();
    }

    @Override
    public LibraryDBDao getDao() {
        return mDao;
    }

}
