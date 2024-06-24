
@a = global i32 0
@b = global i32 0
@ret = global i32 0
define i32 @main() {
entry:
	store i32 10, i32* @a
	store i32 20, i32* @b
	%temp.1 = add i32 a, b
	store i32 %temp.1, i32* @ret
end:
	ret i32 0
}
