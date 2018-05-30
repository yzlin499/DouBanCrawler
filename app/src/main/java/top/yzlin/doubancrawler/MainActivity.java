package top.yzlin.doubancrawler;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import top.yzlin.doubancrawler.crawler.SearchList;

/**
 * @author 严泰清
 */
public class MainActivity extends Activity {
    private EditText et_Search;
    private Button btn_Search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_Search = findViewById(R.id.et_Search);
        btn_Search =findViewById(R.id.btn_search);
        btn_Search.setOnClickListener(this::passData);
    }
    public void passData(View v){
        Intent intent = new Intent(this,SearchResultsActivity.class);
        intent.putExtra("searchInfo",et_Search.getText().toString().trim());
        startActivity(intent);
    }

}
