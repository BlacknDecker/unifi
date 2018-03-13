fun = @(x) (x)^3 - 4*(x)^2 + 5*x -2;
tolx = 1/10; % valori sempre minori
[zero, i] = bisezione(fun, 0, 3, tolx);

function [radice,iterazioni]=bisezione(f,a,b,tolx)
    iterazioni=0;
    if(subs(f,a)*subs(f,b)>0)
        disp('ipotesi non verificata')
    else
        while(true)
            iterazioni=iterazioni+1;
            radice=(a+b)/2;
            if(abs(subs(f,radice))<=tolx)
                break;
            end
            if(subs(f,a)*subs(f,radice)<=0)
                b=radice;
            else
                a=radice;
            end
        end
    end
end
