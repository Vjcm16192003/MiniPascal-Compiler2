
@num1 = global i32 0
@num2 = global i32 0
@result = global i32 0
define i32 @main() {
entry:
	store i32 100, ptr @num1
	store i32 200, ptr @num2
	%temp.1 = icmp sgt i32 num1, num2
	%temp.2 = icmp sgt i32 num1, num2
	br i1 %temp.2, label %itag, label %etag

itag:
	store i32 num1, ptr @result
etag:
	store i32 num2, ptr @result
end:
	ret i32 0
}
