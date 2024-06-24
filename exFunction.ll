
@a = global i32 0
@b = global i32 0
@ret = global i32 0
define i32 @max(i32 %num1, i32 %num2) {
entry:
	%result = alloca i32
	%temp.1 = icmp sgt i32 num1, num2
	br i1 null, label %itag, label %etag

itag:
	store i32 num1, i32* %result
	br label %end
etag:
	store i32 num2, i32* %result
	br label %end
	ret i32 result
null
}

define i32 @main() {
entry:
	store i32 100, i32* @a
	store i32 200, i32* @b
	%t1 = load i32, i32* @a
	%t2 = load i32, i32* @b
	%t3 = load i32, i32* @a
	%t4 = load i32, i32* @b
	%2= call i32 @max(i32 %t3, i32 %t4)
end:
	ret i32 0
}
