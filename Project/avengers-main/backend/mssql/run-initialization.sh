for i in {1..50};
do
    /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P IXlogin#1 -d master -i CreateDataBaseSchema.sql
    if [ $? -eq 0 ]
    then
        echo "CreateDataBaseSchema.sql completed"
        break
    else
        echo "Database not ready yet..."
        sleep 1
    fi
done
