[toc]

## 开发Jira表单的提示词

大佬，你是一名专家安卓工程师，精通安卓App项目研发全流程。请叫我hans7。我有一个安卓项目，请参考现有的 app\src\main\java\com\example\helloworld\FirstFragment.java 和 app\src\main\res\layout\fragment_first.xml ，帮我继续往下添加以下字段：

- 报告人：EditText
- 经办人：EditText
- 截止日期：DatePicker
- 状态：Spinner。选项有：['待办', 'PRD', '技术方案', '开发中', '联调中', '测试', 'UAT', 'Done']
- 更新日期：DatePicker

以上字段都包含一个对应的TextView控件。另外，还需要在最底下增加一个TextView控件，“创建日期”，自动填充初始值为当前日期。

## 实现要求

1. 遵循Don't Repeat Yourself原则，封装重复代码。
2. 仍然使用目前的布局方式：`androidx.constraintlayout.widget.ConstraintLayout`。
