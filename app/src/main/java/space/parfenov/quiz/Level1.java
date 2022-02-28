package space.parfenov.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Level1 extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;

    public int numLeft;
    public int numRight;
    Array array = new Array();//Массив с данными
    Random random = new Random();//Генератор случайных чисел

    public int count = 0; //Счётчик правильных ответов
    public int count1 = 0;

    //макет 1 уровня (universal)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);


        //Переменная text_levels
        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level1);


        //скруглим углы обоих изображений
        final ImageView img_left = (ImageView) findViewById(R.id.img_left);
        img_left.setClipToOutline(true);
        final ImageView img_right = (ImageView) findViewById(R.id.img_right);
        img_right.setClipToOutline(true);

        //Путь к левой и правой TextView
        final TextView text_left = findViewById(R.id.text_left);
        final TextView text_right = findViewById(R.id.text_right);

        //Развернём на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        //Вызов диалогового окна в начале игры
        dialog = new Dialog(this); //Создаём новое диалоговое окно
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//Скрываем заголовок
        dialog.setContentView(R.layout.previewdialog);//Путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//Прозрачный фон окна
        dialog.setCancelable(false);//Нельзя закрыть окно системной кнопкой "назад"



        //Закроем диалоговое окно (Нажатие на "х")
        TextView buttonclose = (TextView) dialog.findViewById(R.id.buttonclose);
        buttonclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Обработка нажатия кнопки
                try {
                    //Вернуться назад, в окно выбора уровня
                    Intent intent = new Intent(Level1.this, GameLevels.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    //Ошибки быть не должно
                }
                dialog.dismiss();
            }
        });


        //Кнопка "Продолжить"
        Button buttoncontinue = (Button) dialog.findViewById(R.id.buttoncontinue);
        buttoncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Закрываем диалоговое окно без перехода к окну выбора уровней
                dialog.dismiss();
            }
        });


        dialog.show();



        //______________________________________________________



        //Вызов диалогового окна в конце игры
        dialogEnd = new Dialog(this); //Создаём новое диалоговое окно
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);//Скрываем заголовок
        dialogEnd.setContentView(R.layout.dialogend);//Путь к макету диалогового окна
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//Прозрачный фон окна
        dialogEnd.setCancelable(false);//Нельзя закрыть окно системной кнопкой "назад"



        //Закроем диалоговое окно (Нажатие на "х")
        TextView buttonclose2 = (TextView) dialogEnd.findViewById(R.id.buttonclose);
        buttonclose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Обработка нажатия кнопки
                try {
                    //Вернуться назад, в окно выбора уровня
                    Intent intent = new Intent(Level1.this, GameLevels.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    //Ошибки быть не должно
                }
                dialogEnd.dismiss();
            }
        });


        //Кнопка "Продолжить"
        Button buttoncontinue2 = (Button) dialogEnd.findViewById(R.id.buttoncontinue);
        buttoncontinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Level1.this, Level2.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {

                }
                //Закрываем диалоговое окно без перехода к окну выбора уровней
                dialogEnd.dismiss();
            }
        });

        //______________________________________________________







        //Кнопка "Назад" на 1 уровне
        Button button_back1 = (Button) findViewById(R.id.button_back1);
        button_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Вернёмся к выбору уровней
                try {
                    Intent intent = new Intent(Level1.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                }
            }
        });


        //Массив для прогресса игры (Показываем шкалу правильных ответов и ошибок)
        final int[] progress = {
                R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
                R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
        };


        //Подключение анимации картинок (плавность появления)
        final Animation a;
        a = AnimationUtils.loadAnimation(Level1.this, R.anim.alpha);


        //Код для картинок 1 уровень
        numLeft = random.nextInt(20);//Генерируем случайное число до 20
        img_left.setImageResource(array.images1[numLeft]);//Находим картинку
        text_left.setText(array.texts1[numLeft]);//Находим текст

        numRight = random.nextInt(20);

        //Чтобы числа не совпадали, иначе мы не сможем сравнить картинки
        while (numLeft == numRight) {
            numRight = random.nextInt(20);
        }

        img_right.setImageResource(array.images1[numRight]);
        text_right.setText(array.texts1[numRight]);

        //Обработка нажатия на левую картинку
        img_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                //Условие касания картинки
                //Если коснулся картинки
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    img_right.setEnabled(false);//Блокируем правую картинку

                    if (array.power1[numLeft] > array.power1[numRight]) {
                        img_left.setImageResource(R.drawable.yess);
                    } else {
                        img_left.setImageResource(R.drawable.noo);
                    }

                    //Если сделал выбор и отпустил палец
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                //Если по левой картинке действительно больше запросов
                    if (array.power1[numLeft] > array.power1[numRight]) {
                        if (count < 10) {
                            count = count + 1;

                        }
                        //В начале игры до прохождения уровня по дефолту закрасим все точки
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        //Определяем правильные ответы и закрашиваем зелёным
                        for(int i=0; i<count;i++){
                            TextView textView = findViewById(progress[i]);
                            textView.setBackgroundResource(R.drawable.style_points_green);

                        }

                    } else {
                        //Хотел сделать так, чтобы мы вычитали 2 балла за ошибку
                        //if(count>0){
                        //   if(count==1){
                        //        count=0;
                        //    }else {
                        //     count=count-2;
                        //    }
                        //}

                        //В начале игры до прохождения уровня по дефолту закрасим все точки
                        //for (int i = 0; i < 9; i++) {
                        //    TextView tv = findViewById(progress[i]);
                        //    tv.setBackgroundResource(R.drawable.style_points);
                        //}

                        //Определяем правильные ответы и закрашиваем зелёным
                        //for(int i=0; i<count;i++){
                        //    TextView textView = findViewById(progress[i]);
                        //    textView.setBackgroundResource(R.drawable.style_points_green);
                        //}
                    }
                    if (count == 10){
                        //Конец уровня
                        dialogEnd.show();
                    } else {
                        numLeft = random.nextInt(20);//Генерируем случайное число до 20
                        img_left.setImageResource(array.images1[numLeft]);//Находим картинку
                        img_left.startAnimation(a);//
                        text_left.setText(array.texts1[numLeft]);//Находим текст

                        numRight = random.nextInt(20);

                        //Чтобы числа не совпадали, иначе мы не сможем сравнить картинки
                        while (numLeft == numRight) {
                            numRight = random.nextInt(20);
                        }

                        img_right.setImageResource(array.images1[numRight]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts1[numRight]);

                        img_right.setEnabled(true);//Включаем обратно правую картинку
                    }
                }

                return true;
            }

        });








        //Обрабатываем нажатие на правую картинку
        img_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                //Условие касания картинки
                //Если коснулся картинки
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    img_left.setEnabled(false);//Блокируем левую картинку

                    if (array.power1[numLeft] < array.power1[numRight]) {
                        img_right.setImageResource(R.drawable.yess);
                    } else {
                        img_right.setImageResource(R.drawable.noo);
                    }

                    //Если сделал выбор и отпустил палец
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //Если по правой картинке действительно больше запросов
                    if (array.power1[numLeft] < array.power1[numRight]) {
                        if (count < 10) {
                            count = count + 1;

                        }
                        //В начале игры до прохождения уровня по дефолту закрасим все точки
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        //Определяем правильные ответы и закрашиваем зелёным
                        for(int i=0; i<count;i++){
                            TextView textView = findViewById(progress[i]);
                            textView.setBackgroundResource(R.drawable.style_points_green);

                        }

                    } else {

                        //можно реализовать функцию, которая учитывает ошибки пользователя и вычитает
                        //2 балла за каждую ошибку (но в таком случае в массив первого уровня нужно
                        // добавить ещё несколько картинок с данными)


                    }
                    if (count == 10){
                        //Конец уровня
                        dialogEnd.show();
                    } else {
                        numLeft = random.nextInt(20);//Генерируем случайное число до 20
                        img_left.setImageResource(array.images1[numLeft]);//Находим картинку
                        img_left.startAnimation(a);//
                        text_left.setText(array.texts1[numLeft]);//Находим текст

                        numRight = random.nextInt(20);

                        //Чтобы числа не совпадали, иначе мы не сможем сравнить картинки
                        while (numLeft == numRight) {
                            numRight = random.nextInt(20);
                        }

                        img_right.setImageResource(array.images1[numRight]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts1[numRight]);

                        img_left.setEnabled(true);//Включаем обратно левую картинку
                    }
                }

                return true;
            }

        });

    }

    //Убираем выход из программы через нажатие системной кнопки "Назад"
    //Чтобы после нажатия на кнопку мы переходили к окну выбора уровня
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(Level1.this, GameLevels.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }

}