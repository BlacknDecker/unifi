x_0 = 5;
alpha = 5;
Table = cell2table(cell(0,3));
Table.Properties.VariableNames = {'i' 'sqrt_alpha' 'err_ass'};

[Table, sqrt_alpha] = newton_sqrt(alpha, x_0, 200, 1/1000000, Table);

uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);
