
@num1 = global i32 0
@num2 = global i32 0
@result = global i32 0
define i32 @main() {
entry:
	store i32 100, i32* @num1
	store i32 200, i32* @num2
	%temp.1 = icmp sgt i32 num1, num2
	br i1 1, label %itag, label %etag

itag:
	store i32 num1, i32* @result
	br label %end

etag:
	store i32 num2, i32* @result
	br label %end

end:
	ret i32 0
}
