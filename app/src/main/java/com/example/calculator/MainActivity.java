package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvResult;
    private String currentInput = "0";
    private String previousInput = null;
    private String operator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        setupButtonListeners(findViewById(android.R.id.content));
    }

    private void setupButtonListeners(View view) {
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup group = (android.view.ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                if (child instanceof Button) {
                    child.setOnClickListener(this);
                } else if (child instanceof android.view.ViewGroup) {
                    setupButtonListeners(child);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        String text = btn.getText().toString();

        switch (text) {
            case "C":
                currentInput = "0";
                previousInput = null;
                operator = null;
                tvResult.setText("0");
                break;

            case "⌫":
                if (currentInput.length() > 1) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                } else {
                    currentInput = "0";
                }
                tvResult.setText(currentInput);
                break;

            case "=":
                if (previousInput != null && operator != null) {
                    double result = calculate(
                            Double.parseDouble(previousInput),
                            Double.parseDouble(currentInput),
                            operator
                    );
                    String finalResult = formatResult(result);

                    // ========== 整蛊文案池（随机抽取） ==========
                    String[] jokes = {
                            "🐟国企鱼正在深度思考中...",
                            "🤔 国企鱼 CPU 已过载，正在重启...",
                            "😤 国企鱼被难住了，正在摇人...",
                            "📞 国企鱼正在拨打老板电话求助...",
                            "☕ 国企鱼先喝杯咖啡，稍等...",
                            "💤 国企鱼已睡着，zzz...",
                            "🎣 国企鱼正在摸鱼，请稍后...",
                            "🤯 国企鱼已崩溃，正在自我修复...",
                            "🔮 国企鱼正在占卜答案...",
                            "🧮 国企鱼正在借隔壁计算器..."
                    };

                    String randomJoke = jokes[(int) (Math.random() * jokes.length)];

                    // 显示整蛊文字
                    tvResult.setText(randomJoke);

                    // 延迟 1.5 秒后显示真实结果
                    tvResult.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvResult.setText(finalResult);
                        }
                    }, 1500);

                    previousInput = null;
                    operator = null;
                    currentInput = finalResult;
                }
                break;

            case "+":
            case "-":
            case "×":
            case "÷":
                if (operator != null && previousInput != null) {
                    double result = calculate(
                            Double.parseDouble(previousInput),
                            Double.parseDouble(currentInput),
                            operator
                    );
                    currentInput = formatResult(result);
                    tvResult.setText(currentInput);
                }
                previousInput = currentInput;
                operator = text;
                currentInput = "0";
                break;

            default:
                if (text.equals(".") && currentInput.contains(".")) break;
                if (currentInput.equals("0") && !text.equals(".")) {
                    currentInput = text;
                } else {
                    currentInput += text;
                }
                tvResult.setText(currentInput);
                break;
        }
    }

    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "×": return a * b;
            case "÷": return b != 0 ? a / b : 0;
            default: return b;
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }
}