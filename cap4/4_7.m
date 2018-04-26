runge = @(x) 1/(1+x^2);
a = -6;
b = 6;

fplot(runge, [a, b])
print('-dpng','runge.png')

n = 2;
while n<=40
    xi = ceby(n, a, b);
    fi = zeros(length(xi), 1);

    for i = 1:length(xi)
        fi(i) = feval(runge, xi(i));
    end

    p = @(x) lagrange(xi, fi, x);
    fplot(p, [a, b])
    print('-dpng', strcat(num2str(n), '.png'));

    n = n + 2;
end
