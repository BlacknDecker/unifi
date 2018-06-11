function If = trapcomp(n, a, b, fun)
    If = 0;
    h = (b-a) / n;
    for i = 1:n-1
        If = If + fun(a + i*h);
    end
    If = (h/2) * (2*If + fun(a) + fun(b));
end

