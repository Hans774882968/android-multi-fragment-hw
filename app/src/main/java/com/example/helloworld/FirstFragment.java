package com.example.helloworld;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.helloworld.databinding.FragmentFirstBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private static final String[] questionTypeOptions = new String[] { "故事", "任务", "改进", "Bug", "Epic", "子任务" };
    private static final String[] priorityOptions = new String[] { "Highest", "High", "Medium", "Low", "Lowest" };
    private static final String[] statusOptions = new String[] { "待办", "PRD", "技术方案", "开发中", "联调中", "测试", "UAT",
            "Done" };

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    private void setupTextWatcher(EditText editText, TextView textView, String prefix) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = prefix + "：" + editText.getText().toString();
                textView.setText(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private ArrayAdapter<String> setupSpinnerAdapter(Spinner spinner, String[] options) {
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(),
        // android.R.layout.simple_dropdown_item_1line, questionTypeOptions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_dropdown_item_1line, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return adapter;
    }

    private void setupSpinnerListeners(Spinner spinner, ArrayAdapter<String> adapter, TextView textView,
            String fieldName) {
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = fieldName + "：" + adapter.getItem(position);
                textView.setText(text);
                parent.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                String text = "None";
                textView.setText(text);
                arg0.setVisibility(View.VISIBLE);
            }
        });

        spinner.setOnFocusChangeListener((v, hasFocus) -> v.setVisibility(View.VISIBLE));
    }

    // 封装日期选择器监听器
    private void setupDatePickerListener(DatePicker datePicker, TextView textView, String prefix) {
        // 初始化日期显示
        updateDateText(datePicker, textView, prefix);

        datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            String dateStr = prefix + "："
                    + String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
            textView.setText(dateStr);
        });
    }

    private void updateDateText(DatePicker datePicker, TextView textView, String prefix) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1; // 月份从0开始
        int day = datePicker.getDayOfMonth();
        String dateStr = prefix + "：" + String.format(Locale.getDefault(), "%d-%02d-%02d", year, month, day);
        textView.setText(dateStr);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(v -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));

        setupTextWatcher(binding.projectBelongTo, binding.projectBelongToText, "所属项目");

        ArrayAdapter<String> questionTypeAdapter = setupSpinnerAdapter(binding.questionType, questionTypeOptions);
        setupSpinnerListeners(binding.questionType, questionTypeAdapter, binding.questionTypeText, "问题类型");

        setupTextWatcher(binding.jiraAbstract, binding.jiraAbstractText, "摘要");

        ArrayAdapter<String> priorityAdapter = setupSpinnerAdapter(binding.priority, priorityOptions);
        setupSpinnerListeners(binding.priority, priorityAdapter, binding.priorityText, "优先级");

        setupTextWatcher(binding.reporter, binding.reporterText, "报告人");
        setupTextWatcher(binding.assignee, binding.assigneeText, "经办人");

        ArrayAdapter<String> statusAdapter = setupSpinnerAdapter(binding.statusSpinner, statusOptions);
        setupSpinnerListeners(binding.statusSpinner, statusAdapter, binding.statusText, "状态");

        // 设置日期选择器
        Calendar calendar = Calendar.getInstance();
        binding.dueDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        binding.updateDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);

        setupDatePickerListener(binding.dueDatePicker, binding.dueDateText, "截止日期");
        setupDatePickerListener(binding.updateDatePicker, binding.updateDateText, "更新日期");

        // 设置创建日期（只读）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = "创建日期：" + sdf.format(calendar.getTime());
        binding.createDateText.setText(currentDate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}