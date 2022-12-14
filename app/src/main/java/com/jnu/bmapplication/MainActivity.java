package com.jnu.bmapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.jnu.bmapplication.Data.Book;
import com.jnu.bmapplication.Data.DataSaver;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainRecycleViewAdapter mainRecycleViewAdapter;
    private static final int MENU_ID_DELETE = 2;
    private static final int MENU_ID_UPDATE = 3;
    private static final int MENU_ID_DETAIL=4;
    private ArrayList<Book>books;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;
    private ImageView imageViewHeader;

    private DataSaver dataSaver=new DataSaver();

    private ActivityResultLauncher<Intent> addDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==EditBookActivity.RESULT_CODE_SUCCESS)
                    {//???????????????????????? extra ????????? Bundle ????????????????????? putExtras() ??? Bundle ?????? Intent ??????
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        String author= bundle.getString("author");
                        String publisher= bundle.getString("publisher");
                        String pubdate= bundle.getString("pubdate");
                        String translator=bundle.getString("translator");
                        int position=bundle.getInt("position");
                        books.add(position, new Book(title,author,publisher,pubdate,R.drawable.book_1,translator));
                        //????????????
                        new DataSaver().Save(this,books);
                        mainRecycleViewAdapter.notifyItemInserted(position);
                    }
                }
            });

    private ActivityResultLauncher<Intent> updateDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==EditBookActivity.RESULT_CODE_SUCCESS)
                    {//???????????????????????? extra ????????? Bundle ????????????????????? putExtras() ??? Bundle ?????? Intent ??????
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        String author= bundle.getString("author");
                        String publisher= bundle.getString("publisher");
                        String pubdate= bundle.getString("pubdate");
                        String translator=bundle.getString("translator");
                        int position=bundle.getInt("position");
                        books.get(position).setTitle(title);
                        books.get(position).setAp(author);
                        books.get(position).setPubli(publisher);
                        books.get(position).setTime(pubdate);
                        books.get(position).setTranslator(translator);
                        books.get(position).setResourceId(R.drawable.book_1);
                        new DataSaver().Save(this,books);
                        mainRecycleViewAdapter.notifyItemChanged(position);
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //????????????
        books=new ArrayList<Book>();
        //DataSaver dataSaver=new DataSaver();
        //????????????
        books=dataSaver.Load(this);

        if(books.size()==0)
        {
            getBook();
        }

        RecyclerView recyclerViewMain=findViewById(R.id.recycleview_main);
        //??????????????????
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);
        //????????????
        mainRecycleViewAdapter=new MainRecycleViewAdapter(books);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

        initSearch();//??????
        initFloatActionButton();//????????????
        init();//???????????????

    }
    //??????????????????
    public void initSearch()
    {
        //SearchView
        SearchView searchView=findViewById(R.id.searchview);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"??????",Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int i=0;
                for(;i<books.size();i++){
                    if(query.equals(books.get(i).getTitle())){
                        //??????
                        Intent intentSearch=new Intent(MainActivity.this,DetailActivity.class);
                        intentSearch.putExtra("position",i);
                        intentSearch.putExtra("title",books.get(i).getTitle());
                        intentSearch.putExtra("author",books.get(i).getAp());
                        intentSearch.putExtra("publisher",books.get(i).getPubli());
                        intentSearch.putExtra("pubdate",books.get(i).getTime());
                        intentSearch.putExtra("translator",books.get(i).getTranslator());
                        startActivity(intentSearch);
                        break;
                    }
                }
                if(i==books.size()){
                    Toast.makeText(MainActivity.this,"?????????",Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
    }
    //?????????????????????
    public void initFloatActionButton()
    {
        FloatingActionButton button=findViewById(R.id.addbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this ,EditBookActivity.class) ;
                intent.putExtra("position",books.size());//?????? extra ?????????????????????????????????????????????????????????
                addDataLauncher.launch(intent);
                //books.add(item.getOrder(),new Book("added"+item.getOrder(),R.drawable.ic_launcher_background));
                // mainRecycleViewAdapter.notifyItemInserted(item.getOrder());
            }
        });
    }
    //?????????????????????
    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.activity_main_navigationView);
        //??????????????????
        mNavigationView.setItemIconTintList(null);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawerlayout);
        //??????toolbar???menu??????
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        //???????????????????????? ??? ?????? ??? app icon????????? ????????????????????????
        mActionBarDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        //????????????ActionBarDrawerToggle?????????????????????
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        mActionBarDrawerToggle.setHomeAsUpIndicator(R.mipmap.ic_flb);
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        mActionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);//setDrawerListener??????
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.drawer_menu_book:
                        Toast.makeText(MainActivity.this , "Book" , Toast.LENGTH_LONG).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.drawer_menu_search:
                        Toast.makeText(MainActivity.this , "Search" , Toast.LENGTH_LONG).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.drawer_menu_set:
                        Toast.makeText(MainActivity.this , "Set" , Toast.LENGTH_LONG).show();
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case MENU_ID_DELETE:
                AlertDialog alertDialog;
                alertDialog=new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                books.remove(item.getOrder());
                                new DataSaver().Save(MainActivity.this,books);
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
            case MENU_ID_UPDATE:
                Intent intentUpdate=new Intent(MainActivity.this,EditBookActivity.class);
                intentUpdate.putExtra("position",item.getOrder());
                intentUpdate.putExtra("title",books.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("author",books.get(item.getOrder()).getAp());
                intentUpdate.putExtra("publisher",books.get(item.getOrder()).getPubli());
                intentUpdate.putExtra("pubdate",books.get(item.getOrder()).getTime());
                intentUpdate.putExtra("translator",books.get(item.getOrder()).getTranslator());
                updateDataLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DETAIL:
                Intent intentDetail=new Intent(MainActivity.this,DetailActivity.class);
                intentDetail.putExtra("position",item.getOrder());
                intentDetail.putExtra("title",books.get(item.getOrder()).getTitle());
                intentDetail.putExtra("author",books.get(item.getOrder()).getAp());
                intentDetail.putExtra("publisher",books.get(item.getOrder()).getPubli());
                intentDetail.putExtra("pubdate",books.get(item.getOrder()).getTime());
                intentDetail.putExtra("translator",books.get(item.getOrder()).getTranslator());
                startActivity(intentDetail);

        }
        return super.onContextItemSelected(item);
    }

    //???????????????
    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder>  {

        private ArrayList<Book> localDataSet;


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

            private final TextView textView;
            private final TextView textView1;
            private final TextView textView2;
            private final TextView textView3;
            private final ImageView imageView;
            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                imageView = view.findViewById(R.id.imageView);
                textView = (TextView) view.findViewById(R.id.biaoti);
                textView1=(TextView) view.findViewById(R.id.ap);
                textView2=(TextView) view.findViewById(R.id.time);
                textView3=(TextView) view.findViewById(R.id.publi);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextView() {
                return textView;
            }

            public TextView getTextView1() {
                return textView1;
            }

            public TextView getTextView2() {
                return textView2;
            }

            public TextView getTextView3() { return textView3;}

            public ImageView getImageView() {
                return imageView;
            }


            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"Update ");
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete ");
                contextMenu.add(0,MENU_ID_DETAIL,getAdapterPosition(),"Detail");

            }
        }


        public MainRecycleViewAdapter(ArrayList<Book> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            //LayoutInflater?????????
            //?????????????????????????????????????????????????????????????????????view
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_layout, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.getTextView().setText(localDataSet.get(position).getTitle());
            viewHolder.getTextView1().setText(localDataSet.get(position).getAp());
            viewHolder.getTextView2().setText(localDataSet.get(position).getTime());
            viewHolder.getTextView3().setText(localDataSet.get(position).getPubli());
            viewHolder.getImageView().setImageResource(localDataSet.get(position).getCoverResourceId());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    public void getBook(){
        books.add(new Book("????????????","?????? ","???????????????????????????","2019-04",R.drawable.book_1,"???"));
        books.add(new Book("??????","??????","???????????????","2012-09",R.drawable.book_1,"???"));
        books.add(new Book("??????","?????????","?????????????????????","2018-12",R.drawable.book_1,"???"));
        books.add(new Book("??????","?????????","???????????????","2022-09",R.drawable.book_1,"???"));
        //books.add(new Book("?????????","?????????","?????????????????????","2010-07",R.drawable.book_1,"???"));
        //books.add(new Book("????????????","?????????","?????????????????????","2011-04",R.drawable.book_1,"???"));
    }
}