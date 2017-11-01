import syslog
syslog.syslog('11111111111111111111111')

import time
import datetime
syslog.syslog('22222222222222222222222')
from azure.storage.blob import ContentSettings
from azure.storage.blob import BlockBlobService
from azure.storage.blob import PublicAccess

syslog.syslog('33333333333333333333333')
data = datetime.datetime.now().strftime("%y-%m-%d-%H-%M")

#block_blob_service.create_container('ubicomp2')

block_blob_service = BlockBlobService(account_name='ubicompdiag325', account_key='e82Z6zgdGoYA0ghET7pxJALGT4lzhir6woOIvz0VTwRWM8Z+imP226Kw4RTym2Uz4NBy0MSN9iPj2vLG9QjvLw==')

#block_blob_service.create_container('ubicomp2', public_access=PublicAccess.Container)


syslog.syslog('44444444444444444444444')
block_blob_service.create_blob_from_path(
    'ubicomp2',
    'log-backup_'+ data +'_.log',
    '/home/ubicomp/ubicomp/nuvem/service.log',
    content_settings=ContentSettings(content_type='text/txt')
            )
syslog.syslog('55555555555555555555555')
