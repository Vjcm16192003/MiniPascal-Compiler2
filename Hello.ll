
@a = global i1 0
@b = global i1 0
@result = global i1 0
define i32 @main() {
entry:
	store i1 true, ptr @a
	store i1 false, ptr @b
	store i1 aandnotb, ptr @result
end:
	ret i32 0
}
