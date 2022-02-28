package space.parfenov.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameLevels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelevels);


        //растянем изображение на весь экран телефона
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //смотрим кнопку "назад" по id(button_back)
        Button button_back = (Button) findViewById(R.id.button_back);


        //обработка нажатия на кнопку "назад"
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //переходим из окна gamelevels в mainactivity
                    Intent intent = new Intent(GameLevels.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
            }
        });


        //Переход на первый уровень

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Intent intent = new Intent(GameLevels.this, Level1.class);
                startActivity(intent);finish();
                }catch (Exception e){

                }
            }
        });

    }


    //изменяем системную кнопку "назад" чтобы мы не могли выйти из приложения сразу
    @Override
    public void onBackPressed(){
        try {


            //переходим из окна gamelevels в mainactivity
            Intent intent = new Intent(GameLevels.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
}
}