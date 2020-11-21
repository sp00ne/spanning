# Spanning [![](https://jitpack.io/v/sp00ne/spanning.svg)](https://jitpack.io/#sp00ne/spanning)
A library to simplify your Spannable usage with many handy extensions
## How to get it
In the `build.gradle` file of your project, include the dependency like so:
```
	dependencies {
	        implementation 'com.github.sp00ne:spanning:0.1.0'
	}
```
## How to use it
The usage of this library is pretty straight-forward. To start building the spannable, we begin with the simple example of just returing a `SpannedString`:
```
  spannable("some text without any candy")
```
If you wish to add Spans to it, you simply open up a block, like so:

### Single span
```
spannable("some text with candy") {
  setTextColor(color)
}
```

### Multiple spans
Which will cause the entire text("some text with candy") to get the text color specified in the block. Spans can also be applied on top of eachother like this:
```
spannable("this has many effects") {
  setTextColor(color)
  setUnderline()
  setStrikethrough()
}
```
which results in "this has many effects" being colored, underlined and strikethrough.

### Appending spans
If you wish to append multiple spans, there are supported functions for this in the library.
```
spannable(first text") appendSpace spannable("second text")
```
which simply appends and adds a space character inbetween the two. Resulting in `first text second text` It is also possible for a line break like so:
```
spannable("first text") appendLine spannable("second text on a new line")
```
results in
```
first text
second text on a new line
```

## What is supported
Running the demo will showcase what is supported. As of right now there is
* `setTextSize`
* `setTextSizeDp`
* `setTextColor`
* `setUnderline`
* `setStrikethrough`
* `setScale`
* `setBold`
* `setItalic`
* `setBackgroundColor`
* `setFont`
* `setTextAppearance`

## Demo
[![](https://i.ibb.co/LpJm2f2/Screenshot-1605967464.png)]
