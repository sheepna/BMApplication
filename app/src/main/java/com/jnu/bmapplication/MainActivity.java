package com.jnu.bmapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.bmapplication.Data.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Book>books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        books=new ArrayList<Book>();
        getBook();

        RecyclerView recyclerViewMain=findViewById(R.id.recycleview_main);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);
        MainRecycleViewAdapter mainRecycleViewAdapter=new MainRecycleViewAdapter(books);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);
    }
    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

        private ArrayList<Book> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView;
            private final TextView textView1;
            private final TextView textView2;
            private final ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                imageView = view.findViewById(R.id.imageView);
                textView = (TextView) view.findViewById(R.id.biaoti);
                textView1=(TextView) view.findViewById(R.id.ap);
                textView2=(TextView) view.findViewById(R.id.time);
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

            public ImageView getImageView() {
                return imageView;
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used
         *                by RecyclerView.
         */
        public MainRecycleViewAdapter(ArrayList<Book> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_layout, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element


            viewHolder.getTextView().setText(localDataSet.get(position).getTitle());
            viewHolder.getTextView1().setText(localDataSet.get(position).getAp());
            viewHolder.getTextView2().setText(localDataSet.get(position).getTime());
            viewHolder.getImageView().setImageResource(localDataSet.get(position).getCoverResourceId());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    public void getBook(){
        books.add(new Book("数学","陈天 著，清华大学出版社","2002-0905",R.drawable.book_1));
    }
}