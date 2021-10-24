package com.lzk.calculator;

import com.lzk.myException.NegativeNumberException;
import com.lzk.myException.ZeroDivException;
import com.lzk.utils.CalculatorUtils;
import com.lzk.utils.ExpressionUtils;
import com.lzk.utils.IOUtils;

import java.io.IOException;
import java.util.Locale;

public class Calculator {
    public Calculator() {}
    public void generate(int num,String topicPath,String answerPath) throws IOException {
        if (! (num > 0)){return;}
        for (int i = 0;i < num;i++){
            String expression = ExpressionUtils.getExpressionAsString();
            String[] strings = expression.split("=");
            IOUtils.frequentWrite(topicPath,((i+1)+"."+strings[0]+"=").trim()+"\n");
            IOUtils.frequentWrite(answerPath,(i+1)+"."+strings[1].trim()+"\n");
        }
    }
    public void check(String topicPath,String answerPath,String report) throws IOException {
        String topic;
        String answer;
        int correct = 0;
        int error = 0;
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        while((topic = IOUtils.frequentRead(topicPath))!=null){
            String[] str = topic.split("\\.");
            String no = str[0];
            topic = str[1];
            answer = IOUtils.frequentRead(answerPath).split("\\.")[1];
            try {
                String result = CalculatorUtils.calculate(topic).pop();
                boolean flag = result.equals(answer);
                if (flag == true){correct++;builder1.append(no+",");}
                else {error++;builder2.append(no+",");}
            } catch (ZeroDivException e) {
                e.printStackTrace();
            } catch (NegativeNumberException e) {
                e.printStackTrace();
            }
        }
        IOUtils.write(report,"correct:"+correct+"{"+builder1.toString()+"}\n"+"error:"+error+"{"+builder2.toString()+"}");
        System.out.println("correct:"+correct+"{"+builder1.toString()+"}");
        System.out.println("error:"+error+"{"+builder2.toString()+"}");
    }
}
