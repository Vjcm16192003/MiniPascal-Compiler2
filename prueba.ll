@a = global i32 0
@b = global i32 0
@ret = global i32 0
define i32 @main() {
entry:
	%temp.1 = 10
	store i32 %temp.1, i32* @a
	%temp.2 = 20
	store i32 %temp.2, i32* @b
	%temp.3 = add i32 a, b
	%temp.4 = %temp.3
	store i32 %temp.4, i32* @ret
	ret i32 0
}
