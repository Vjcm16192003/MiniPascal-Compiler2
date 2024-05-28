
@a = global i1 0
@b = global i1 0
@result = global i1 0
define i32 @main() {
entry:
	store i1 1, i1* @a
	store i1 0, i1* @b
	store i1 , i1* @result
end:
	ret i32 0
}
